package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class BookViewTest
{

	private ServerMock server;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	public void testOpenBook()
	{
		PlayerMock player = server.addPlayer();

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.addPage("Hello World");
		book.setItemMeta(meta);

		player.openBook(book);
		BookView view = player.getOpenBook();

		assertNotNull(view);
		assertEquals(player, view.getPlayer());
		assertEquals(book, view.getBook());
	}

	@Test
	public void testCloseBook()
	{
		PlayerMock player = server.addPlayer();

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.addPage("Hello World");
		book.setItemMeta(meta);

		player.openBook(book);
		assertNotNull(player.getOpenBook());

		player.closeBook();
		assertNull(player.getOpenBook());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOpenNonBook()
	{
		PlayerMock player = server.addPlayer();

		ItemStack item = new ItemStack(Material.DIAMOND);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Not a book");
		item.setItemMeta(meta);

		player.openBook(item);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOpenBookWithoutMeta()
	{
		PlayerMock player = server.addPlayer();

		// This book has no ItemMeta
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
		player.openBook(item);
	}

	@Test
	public void testChangePage()
	{
		PlayerMock player = server.addPlayer();

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.addPage("Hello World", "This is page number 2!");
		book.setItemMeta(meta);

		player.openBook(book);
		BookView view = player.getOpenBook();

		assertEquals(2, view.getPages());
		assertEquals(1, view.getCurrentPage());

		view.setCurrentPage(2);
		assertEquals(2, view.getCurrentPage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPageTooLow()
	{
		PlayerMock player = server.addPlayer();

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.addPage("Hello World");
		book.setItemMeta(meta);

		player.openBook(book);
		BookView view = player.getOpenBook();

		view.setCurrentPage(-10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPageTooHigh()
	{
		PlayerMock player = server.addPlayer();

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.addPage("Hello World");
		book.setItemMeta(meta);

		player.openBook(book);
		BookView view = player.getOpenBook();

		view.setCurrentPage(10);
	}
}
