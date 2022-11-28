package es.grise.upm.profundizacion.mocking_ejercicio_1;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import es.grise.upm.profundizacion.mocking_ejercicio_1.Client;


public class SubscriptionServiceTest {

//State tests

	//No se puede añadir un Client null a la lista subscribers.
	@Test(expected = NullClientException.class)
	public void test_3() throws Exception {
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(null);
	}	

	//Al añadir un Clientmediante addSubscriber(), éste Client se almacena en la lista subscribers.
	@Test
	public void test_4() throws Exception {
		Client cl = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		assertTrue(sus.subscribers.contains(cl));
	}	
	
	
	//No se puede añadir dos veces el mismo Client mediante addSubscriber() a la lista subscribers 
	//Al hacerlo, se lanza la excepción ExistingClientException.
	@Test(expected = ExistingClientException.class)
	public void test_5() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		SubscriptionService sus = new SubscriptionService();
		Client cl = Mockito.mock(Client.class);
		sus.addSubscriber(cl);
		sus.addSubscriber(cl);
	}	
	
	//Al añadir varios Client mediante addSubscriber(), todos los Clientse almacenan en la lista subscribers.
	@Test
	public void test_6() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		SubscriptionService sus = new SubscriptionService();
		Client cl = Mockito.mock(Client.class);
		Client cl2 = Mockito.mock(Client.class);
		sus.addSubscriber(cl);
		sus.addSubscriber(cl2);
		assertTrue(sus.subscribers.contains(cl));
		assertTrue(sus.subscribers.contains(cl2));
		
	}	
	//No se puede eliminar (usando removeSubscriber() un Client null de la lista subscribers. Al hacerlo, se lanza la excepción NullClientException.
	@Test (expected = NullClientException.class)
	public void test_7() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		SubscriptionService sus = new SubscriptionService();
		sus.removeSubscriber(null);
	}	
	
	//No se puede eliminar (usando removeSubscriber() un Client que no está almacenado en la lista subscribers. Al hacerlo, se lanza la excepción NonExistingClientException.
	@Test (expected = NonExistingClientException.class)
	public void test_8() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.removeSubscriber(cl);
	}	
	//Se puede eliminar correctamente (usando removeSubscriber() un Client almacenado en la lista subscribers.
	@Test
	public void test_9() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		sus.removeSubscriber(cl);
		assertFalse(sus.subscribers.contains(cl));
	}	
	//No se puede eliminar (usando removeSubscriber() dos veces el mismo Client de la lista subscribers. Al hacerlo, se lanza la excepción NonExistingClientException.
	@Test (expected = NonExistingClientException.class)
	public void test_10() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		sus.removeSubscriber(cl);
		sus.removeSubscriber(cl);
	}
	//Se pueden eliminar correctamente (usando removeSubscriber() varios Client almacenados en la lista subscribers.
	@Test
	public void test_11() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		Client cl2 = Mockito.mock(Client.class);
		Client cl3 = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		sus.addSubscriber(cl2);
		sus.addSubscriber(cl3);
		sus.removeSubscriber(cl);
		sus.removeSubscriber(cl2);
		assertFalse(sus.subscribers.contains(cl));
		assertFalse(sus.subscribers.contains(cl2));
	}	
	//Se pueden eliminar correctamente (usando removeSubscriber() todos los Client almacenados en la lista subscribers.
	@Test
	public void test_12() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		Client cl2 = Mockito.mock(Client.class);
		Client cl3 = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		sus.addSubscriber(cl2);
		sus.addSubscriber(cl3);
		sus.removeSubscriber(cl);
		sus.removeSubscriber(cl2);
		sus.removeSubscriber(cl3);
		assertTrue(sus.subscribers.isEmpty());

	}	
//Interaction tests

	//Un Client suscrito recibe mensajes (método receiveMessage() si tiene email (método hasEmail() == true).
	@Test
	public void test_13() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		Message ms = Mockito.mock(Message.class);
		Mockito.when(cl.hasEmail()).thenReturn(true);
		if(cl.hasEmail()) {
		sus.sendMessage(ms);
		}
		Mockito.verify(cl, Mockito.times(1)).receiveMessage(ms);

	}	
	//Un Client suscrito no recibe mensajes (método receiveMessage() si no tiene email (método hasEmail() == false).
	@Test
	public void test_14() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		Message ms = Mockito.mock(Message.class);
		Mockito.when(cl.hasEmail()).thenReturn(false);
		if(cl.hasEmail()) {
		sus.sendMessage(ms);
		}
		Mockito.verify(cl, Mockito.times(0)).receiveMessage(ms);

	}	
	//Varios  Client suscritos reciben mensajes (método receiveMessage() si tienen email (método hasEmail() == true).
	@Test
	public void test_15() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		Client cl2 = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		sus.addSubscriber(cl2);
		Message ms = Mockito.mock(Message.class);
		Mockito.when(cl.hasEmail()).thenReturn(true);
		Mockito.when(cl2.hasEmail()).thenReturn(true);
		if(cl.hasEmail() || cl2.hasEmail()) {
		sus.sendMessage(ms);
		}
		Mockito.verify(cl, Mockito.times(1)).receiveMessage(ms);
		Mockito.verify(cl2, Mockito.times(1)).receiveMessage(ms);
	}	
	//Al des-suscribir un Client éste no recibe mensajes (método receiveMessage()).
	@Test
	public void test_16() throws Exception {
		//Collection <Client> subscribers = new ArrayList<Client>();
		Client cl = Mockito.mock(Client.class);
		SubscriptionService sus = new SubscriptionService();
		sus.addSubscriber(cl);
		Message ms = Mockito.mock(Message.class);
		sus.removeSubscriber(cl);
		Mockito.when(cl.hasEmail()).thenReturn(false);
		if(cl.hasEmail()) {
		sus.sendMessage(ms);
		}
		Mockito.verify(cl, Mockito.times(0)).receiveMessage(ms);

	}	
}
