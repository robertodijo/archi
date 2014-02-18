/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.archimatetool.model.IArchimateFactory;
import com.archimatetool.model.IDiagramModelConnection;
import com.archimatetool.model.IDiagramModelObject;
import com.archimatetool.model.IFontAttribute;


@SuppressWarnings("nls")
public class DiagramModelConnectionTests {
    
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DiagramModelConnectionTests.class);
    }
    
    private IDiagramModelObject source, target;
    private IDiagramModelConnection connection;
    
    // ---------------------------------------------------------------------------------------------
    // BEFORE AND AFTER METHODS GO HERE 
    // ---------------------------------------------------------------------------------------------
    
    @Before
    public void runBeforeEachTest() {
        source = IArchimateFactory.eINSTANCE.createDiagramModelGroup();
        target = IArchimateFactory.eINSTANCE.createDiagramModelGroup();
        connection = IArchimateFactory.eINSTANCE.createDiagramModelConnection();
    }
    
    // ---------------------------------------------------------------------------------------------
    // Tests
    // ---------------------------------------------------------------------------------------------
    
    @Test
    public void testGetBendpoints() {
        assertTrue(connection.getBendpoints().isEmpty());
    }

    @Test
    public void testGetCopy() {
        connection.setSource(source);
        connection.setTarget(target);
        connection.getProperties().add(IArchimateFactory.eINSTANCE.createProperty());
        
        IDiagramModelConnection copy = (IDiagramModelConnection)connection.getCopy();
        
        assertNotSame(target, copy);
        assertNotSame(connection.getProperties(), copy.getProperties());
        assertEquals(connection.getProperties().size(), copy.getProperties().size());
        assertNotSame(connection.getBendpoints(), copy.getBendpoints());
        assertNull(copy.getSource());
        assertNull(copy.getTarget());
        assertEquals(connection.getDocumentation(), copy.getDocumentation());
        assertEquals(connection.getType(), copy.getType());
    }

    @Test
    public void testGetDefaultTextAlignment() {
        assertEquals(IFontAttribute.TEXT_ALIGNMENT_CENTER, connection.getDefaultTextAlignment());
    }

    @Test
    public void testGetDocumentation() {
        CommonTests.testGetDocumentation(connection);
    }
        
    @Test
    public void testGetFont() {
        assertNull(connection.getFont());
        connection.setFont("Arial");
        assertEquals("Arial", connection.getFont());
    }
    
    @Test
    public void testGetFontColor() {
        assertNull(connection.getFontColor());
        connection.setFontColor("#ffffff");
        assertEquals("#ffffff", connection.getFontColor());
    }
    
    @Test
    public void testGetLineColor() {
        assertNull(connection.getLineColor());
        connection.setLineColor("#ffffff");
        assertEquals("#ffffff", connection.getLineColor());
    }
    
    @Test
    public void testGetLineWidth() {
        assertEquals(1, connection.getLineWidth());
        connection.setLineWidth(2);
        assertEquals(2, connection.getLineWidth());
    }
    
    @Test
    public void testGetProperties() {
        CommonTests.testProperties(connection);
    }

    @Test
    public void testGetSource() {
        assertNull(connection.getSource());
        connection.setSource(source);
        assertSame(source, connection.getSource());
    }
    
    @Test
    public void testGetTarget() {
        assertNull(connection.getTarget());
        connection.setTarget(target);
        assertSame(target, connection.getTarget());
    }
    
    @Test
    public void testGetTextAlignment() {
        assertEquals(IFontAttribute.TEXT_ALIGNMENT_NONE, connection.getTextAlignment());
        connection.setTextAlignment(2);
        assertEquals(2, connection.getTextAlignment());
    }
    
    @Test
    public void testGetTextPosition() {
        assertEquals(IDiagramModelConnection.CONNECTION_TEXT_POSITION_MIDDLE, connection.getTextPosition());
        connection.setTextPosition(2);
        assertEquals(2, connection.getTextPosition());
    }
    
    @Test
    public void testGetType() {
        assertEquals(0, connection.getType());
        connection.setType(2);
        assertEquals(2, connection.getType());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testConnectNull() {
        connection.connect(null, null);
    }
    
    @Test
    public void testConnect() {
        testPreConnect();
        connection.connect(source, target);
        testPostConnect();
    }

    @Test
    public void testDisconnect() {
        testPreConnect();
        connection.connect(source, target);
        testPostConnect();
        
        connection.disconnect();
        testPostDisconnect();
    }
    
    @Test
    public void testReconnect() {
        testPreConnect();
        connection.connect(source, target);
        testPostConnect();
        
        connection.disconnect();
        testPostDisconnect();
        
        connection.reconnect();
        testPostConnect();
    }
    
    private void testPreConnect() {
        assertNull(connection.getSource());
        assertNull(connection.getTarget());
        assertFalse(source.getSourceConnections().contains(source));
        assertFalse(target.getTargetConnections().contains(target));
    }
    
    private void testPostConnect() {
        assertEquals(source, connection.getSource());
        assertEquals(target, connection.getTarget());
        
        assertTrue(source.getSourceConnections().contains(connection));
        assertFalse(source.getTargetConnections().contains(connection));

        assertTrue(target.getTargetConnections().contains(connection));
        assertFalse(target.getSourceConnections().contains(connection));
    }

    private void testPostDisconnect() {
        assertFalse(source.getSourceConnections().contains(connection));
        assertFalse(source.getTargetConnections().contains(connection));

        assertFalse(target.getTargetConnections().contains(connection));
        assertFalse(target.getSourceConnections().contains(connection));        
    }
}