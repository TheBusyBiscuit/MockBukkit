package be.seeseemelk.mockbukkit.inventory;

import java.util.Objects;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * This class represents the view of a written book as seen by a {@link Player}. It is very similar to an
 * {@link InventoryView} but exclusively for written books.
 * 
 * @author TheBusyBiscuit
 *
 */
public class BookView
{

	private final Player player;
	private final ItemStack book;
	private final int maxPages;
	private int page = 1;

	public BookView(Player player, ItemStack book, int page)
	{
		Validate.notNull(player);
		Validate.notNull(book);

		this.maxPages = getMaxPages(book);
		this.player = player;
		this.book = book;
		setCurrentPage(page);
	}

	public BookView(Player player, ItemStack book)
	{
		this(player, book, 1);
	}

	private int getMaxPages(ItemStack book)
	{
		if (book.hasItemMeta())
		{
			ItemMeta meta = book.getItemMeta();

			if (meta instanceof BookMeta)
			{
				return ((BookMeta) meta).getPageCount();
			}
		}

		throw new IllegalArgumentException("The given 'book' does not seem to be a valid book!");
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + Objects.hash(player, book, page, maxPages);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (!(obj instanceof BookView))
		{
			return false;
		}
		BookView other = (BookView) obj;
		return player.equals(other.player) && book.equals(other.book);
	}

	/**
	 * This returns the {@link Player} who is viewing this book.
	 * 
	 * @return The viewing {@link Player}
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * This returns the page from the book the {@link Player} is currently looking at.
	 * 
	 * @return The currently "visible" page
	 */
	public int getCurrentPage()
	{
		return page;
	}

	/**
	 * This returns the total amount of pages this book has.
	 * 
	 * @return total amount of pages of this book
	 */
	public int getPages()
	{
		return maxPages;
	}

	/**
	 * This sets the currently viewed page to the given parameter. The parameter must lay between 1 and
	 * {@link #getPages()}.
	 * 
	 * @param page The new page
	 */
	public void setCurrentPage(int page)
	{
		if (page < 1 || page > maxPages)
		{
			throw new IllegalArgumentException("Page " + page + " does not exist!");
		}

		this.page = page;
	}

	/**
	 * This returns a copy of the book the {@link Player} had opened.
	 * 
	 * @return A copy of the book that was opened
	 */
	public ItemStack getBook()
	{
		return book.clone();
	}

}
