package es.grise.upm.profundizacion.tema8;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Before;

public class SubscriptionServiceTest {
	
	private static final int ZERO_TIME = 0;
	private static final int ONE_TIME = 1;
	private static final int TWO_TIME = 2;
	private SubscriptionService servicio = new SubscriptionService();
	private Client cliente1 = mock(Client.class);
	private Client cliente2 = mock(Client.class);
	private Message mensaje = mock(Message.class);
	
	@Test(expected = NullClientException.class)
	public void clienteNulo()
			throws ExistingClientException, NullClientException {
		servicio.addSubscriber(null);
	}
	
	@Test
	public void añadirCliente() throws NullClientException, ExistingClientException {
		servicio.addSubscriber(this.cliente1);
		assertEquals(this.servicio.subscribers.size(),ONE_TIME);
	}
	
	@Test (expected = ExistingClientException.class)
	public void clienteExistente() throws NullClientException, ExistingClientException {
		servicio.addSubscriber(this.cliente1);
		servicio.addSubscriber(this.cliente1);
	}
	
	@Test
	public void añadir2Clientes() throws NullClientException, ExistingClientException {
		servicio.addSubscriber(this.cliente1);
		servicio.addSubscriber(this.cliente2);
		assertEquals(this.servicio.subscribers.size(),TWO_TIME);
	}
	
	@Test (expected = NullClientException.class)
	public void noEliminarClienteNull() throws NullClientException, NonExistingClientException {
		this.servicio.removeSubscriber(null);
	}
	
	@Test (expected = NonExistingClientException.class)
	public void noEliminarClienteQueNoEsteEnLista() throws NullClientException, NonExistingClientException {
		this.servicio.removeSubscriber(this.cliente1);
	}
	
	@Test
	public void eliminarClienteExistenteLista() throws NullClientException, ExistingClientException, NonExistingClientException {
		this.servicio.addSubscriber(this.cliente1);
		this.servicio.removeSubscriber(this.cliente1);
		assertEquals(this.servicio.subscribers.size(),ZERO_TIME);
	}
	
	@Test (expected = NonExistingClientException.class)
	public void noEliminarDosVecesAlMismoCliente() throws NullClientException, NonExistingClientException, ExistingClientException {
		this.servicio.addSubscriber(this.cliente1);
		this.servicio.removeSubscriber(this.cliente1);
		this.servicio.removeSubscriber(this.cliente1);
	}
	
	@Test
	public void eliminarVariosClientes() throws NullClientException, ExistingClientException, NonExistingClientException {
		this.servicio.addSubscriber(this.cliente1);
		this.servicio.addSubscriber(this.cliente2);
		this.servicio.removeSubscriber(this.cliente1);
		this.servicio.removeSubscriber(this.cliente2);
		assertEquals(this.servicio.subscribers.size(),ZERO_TIME);
	}
	
	@Test 
	public void recibirEmail() throws NullClientException, ExistingClientException {		
		when(this.cliente1.hasEmail()).thenReturn(true);
		this.servicio.addSubscriber(this.cliente1);
		this.servicio.sendMessage(this.mensaje);	
		verify(this.cliente1,times(ONE_TIME)).receiveMessage(this.mensaje);		
	}
	
	@Test 
	public void noRecibirMensajeSiNoMail() throws NullClientException, ExistingClientException {		
		when(this.cliente1.hasEmail()).thenReturn(false);
		this.servicio.addSubscriber(this.cliente1);
		this.servicio.sendMessage(this.mensaje);	
		verify(this.cliente1,times(ZERO_TIME)).receiveMessage(this.mensaje);		
	}
	
	@Test
	public void todosRecibenMensaje() throws NullClientException, ExistingClientException {
		when(this.cliente1.hasEmail()).thenReturn(true);
		when(this.cliente2.hasEmail()).thenReturn(true);
		this.servicio.addSubscriber(this.cliente1);
		this.servicio.addSubscriber(this.cliente2);
		this.servicio.sendMessage(this.mensaje);
		verify(this.cliente1,times(ONE_TIME)).receiveMessage(this.mensaje);
		verify(this.cliente2,times(ONE_TIME)).receiveMessage(this.mensaje);
	}
	
	@Test 
	public void noRecibirMensajeSiNoEstaSuscrito() throws NullClientException, ExistingClientException, NonExistingClientException {		
		when(cliente1.hasEmail()).thenReturn(true);
		this.servicio.addSubscriber(this.cliente1);
		this.servicio.removeSubscriber(this.cliente1);
		this.servicio.sendMessage(this.mensaje);	
		verify(this.cliente1,never()).receiveMessage(this.mensaje);		
	}
	
}
