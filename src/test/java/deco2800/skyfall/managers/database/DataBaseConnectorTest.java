package deco2800.skyfall.managers.database;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DataBaseConnectorTest {
    @Mock
    private DataBaseConnector dataBaseConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement stmt;
    @Mock
    private ResultSet result;

    @Before
    public void setup(){
        assertNotNull(dataBaseConnector);
        when(dataBaseConnector.getConnection()).thenReturn(connection);
    }
}
