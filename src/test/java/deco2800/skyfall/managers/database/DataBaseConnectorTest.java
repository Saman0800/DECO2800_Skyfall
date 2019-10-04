package deco2800.skyfall.managers.database;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.FileInputStream;
import java.io.InputStream;
import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.xml.sax.InputSource;


public class DataBaseConnectorTest extends DBTestCase {
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

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(new FileInputStream("thing.xml"));
    }
}
