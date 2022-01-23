package com.example.config;

import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.DatabaseUnitException;
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
import java.io.FileWriter;
import java.sql.SQLException;

@ApplicationScoped
@Slf4j
public class DatabaseIntegrationTestingConfig {
    private final Flyway flyway;

    @Inject
    public DatabaseIntegrationTestingConfig(Flyway flyway) {
        this.flyway = flyway;
    }

    void startup(@Observes StartupEvent event) {
        flyway.migrate();
        generateDTDSchema();
    }

    private void generateDTDSchema() {
        try {
            IDatabaseConnection connection = createConnection();
            IDataSet dataSet = connection.createDataSet();
            FileWriter fileWriter = new FileWriter("src/test/resources/db-schema.dtd");
            try (fileWriter) {
                FlatDtdWriter datasetWriter = new FlatDtdWriter(fileWriter);
                datasetWriter.setContentModel(FlatDtdWriter.CHOICE);
                datasetWriter.write(dataSet);
            } finally {
                fileWriter.close();
            }
        } catch (Exception exception) {
            log.error("Failed to generate DTD schema", exception);
            throw new RuntimeException(exception);
        }
    }

    private IDatabaseConnection createConnection() throws DatabaseUnitException, SQLException {
        DataSource dataSource = flyway.getConfiguration().getDataSource();
        IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());
        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        return connection;
    }
}
