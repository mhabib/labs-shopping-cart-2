package com.shapestone.labs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ShoppingCartTest {
    private ShoppingCartImpl shoppingCart;

    @Test
    public void testOfAddingToShoppingCartShouldContainOneItem() throws Exception {
    	  // given
        List<ProductItem> productItemList = new ArrayList<ProductItem>();
        productItemList.add(new ProductItem("stapler", 1, 5.99D));
        ShoppingCartApi api = mock(ShoppingCartApi.class);
        when(api.getAllItems()).thenReturn(productItemList);

        shoppingCart = new ShoppingCartImpl();
        shoppingCart.setShoppingCartApi(api);
        ProductItem item = new ProductItem("stapler", 1, 5.99D);

        // when
        shoppingCart.addItem(item);

        // then
        assertThat(shoppingCart, is(not(nullValue())));
        assertThat(shoppingCart.getOrderItemCount(), is(equalTo(1)));
        assertThat(shoppingCart.getProductItemCount(), is(equalTo(1)));
        assertThat(shoppingCart.calculateTotal(), is(equalTo(5.99D)));
    }

    @Test
    public void testOfAddingProductItemWithACountOfTheeShouldYieldTheCorrectTotal() throws Exception {
        // given
    	ShoppingCartApi api = mock(ShoppingCartApi.class);
        shoppingCart = new ShoppingCartImpl();
        shoppingCart.setShoppingCartApi(api);
        ProductItem item = new ProductItem("stapler", 3, 5.99D);
        
        List<ProductItem> productItemList = new ArrayList<ProductItem>();
        productItemList.add(item);
        when(api.getAllItems()).thenReturn(productItemList);
        
        // when
        shoppingCart.addItem(item);

        // then
        assertThat(shoppingCart, is(not(nullValue())));
        assertThat(shoppingCart.getOrderItemCount(), is(equalTo(3)));
        assertThat(shoppingCart.getProductItemCount(), is(equalTo(1)));
        assertThat(shoppingCart.calculateTotal(), is(equalTo(3*5.99D)));
    }

    @Test
    public void testOfAddingMultipleProductItemsWithDifferentCountsShouldYieldTheCorrectTotalAndCounts() throws Exception {
        // given
    	ShoppingCartApi api = mock(ShoppingCartApi.class);
        shoppingCart = new ShoppingCartImpl();
        shoppingCart.setShoppingCartApi(api);
        ProductItem item1 = new ProductItem("stapler", 3, 5.99D);
        ProductItem item2 = new ProductItem("pencils", 10, 0.49D);

        List<ProductItem> productItemList = new ArrayList<ProductItem>();
        productItemList.add(item1);
        productItemList.add(item2);
        when(api.getAllItems()).thenReturn(productItemList);
        // when
        shoppingCart.addItem(item1);
        shoppingCart.addItem(item2);

        // then
        assertThat(shoppingCart, is(not(nullValue())));
        assertThat(shoppingCart.getOrderItemCount(), is(equalTo(13)));
        assertThat(shoppingCart.getProductItemCount(), is(equalTo(2)));
        assertThat(shoppingCart.calculateTotal(), is(equalTo(3*5.99D + 10*0.49)));
    }

    @Test
    public void testOfAddingMultipleProductItemsAndRemovingOneItemShouldYieldTheCorrectTotalAndCounts() throws Exception {
        // given
    	ShoppingCartApi api = mock(ShoppingCartApi.class);
        shoppingCart = new ShoppingCartImpl();
        shoppingCart.setShoppingCartApi(api);
        ProductItem item1 = new ProductItem("stapler", 3, 5.99D);
        ProductItem item2 = new ProductItem("pencils", 10, 0.49D);

        List<ProductItem> productItemList = new ArrayList<ProductItem>();
        productItemList.add(item2);
        productItemList.remove(item1);
        when(api.getAllItems()).thenReturn(productItemList);
        
        // when
        shoppingCart.addItem(item1);
        shoppingCart.addItem(item2);
        shoppingCart.removeItem(item1);

        // then
        assertThat(shoppingCart, is(not(nullValue())));
        assertThat(shoppingCart.getOrderItemCount(), is(equalTo(10)));
        assertThat(shoppingCart.getProductItemCount(), is(equalTo(1)));
        assertThat(shoppingCart.calculateTotal(), is(equalTo(10*0.49D)));
    }

    @Test
    public void testOfAddingMultipleProductItemsAndClearingTheShoppingCartShouldYieldTheCorrectTotalAndCounts() throws Exception {
        // given
    	ShoppingCartApi api = mock(ShoppingCartApi.class);
        shoppingCart = new ShoppingCartImpl();
        shoppingCart.setShoppingCartApi(api);
        ProductItem item1 = new ProductItem("stapler", 3, 5.99D);
        ProductItem item2 = new ProductItem("pencils", 10, 0.49D);

        List<ProductItem> productItemList = new ArrayList<ProductItem>();
        when(api.getAllItems()).thenReturn(productItemList);
        
        // when
        shoppingCart.addItem(item1);
        shoppingCart.addItem(item2);
        shoppingCart.emptyShoppingCart();

        // then
        assertThat(shoppingCart, is(not(nullValue())));
        assertThat(shoppingCart.getOrderItemCount(), is(equalTo(0)));
        assertThat(shoppingCart.getProductItemCount(), is(equalTo(0)));
        assertThat(shoppingCart.calculateTotal(), is(equalTo(0D)));
    }
}
