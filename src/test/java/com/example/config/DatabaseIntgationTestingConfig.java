package com.example.config;

import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdWriter;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

@ApplicationScoped
@Slf4j
public class DatabaseIntgationTestingConfig {
    private final Flyway flyway;

    @Inject
    public DatabaseIntgationTestingConfig(Flyway flyway) {
        this.flyway = flyway;
    }

    void startup(@Observes StartupEvent event) {
        flyway.migrate();
        generateDTDSchema();
    }

    private void generateDTDSchema() {
        try {
            DataSource dataSource = flyway.getConfiguration().getDataSource();
            IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());
            DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());

            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream("src/test/resources/db-schema.dtd"));
            IDataSet dataSet = connection.createDataSet();

            FlatDtdWriter datasetWriter = new FlatDtdWriter(output);
            datasetWriter.setContentModel(FlatDtdWriter.CHOICE);
            datasetWriter.write(dataSet);
        } catch (Exception exception) {
            log.error("Failed to generate DTD schema");
            throw new RuntimeException(exception);
        }
    }
}
