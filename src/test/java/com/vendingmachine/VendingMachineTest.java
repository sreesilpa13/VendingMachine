package com.vendingmachine;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vendingmachine.Bucket;
import com.vendingmachine.Coin;
import com.vendingmachine.Item;
import com.vendingmachine.NotSufficientChangeException;
import com.vendingmachine.SoldOutException;
import com.vendingmachine.VendingMachine;
import com.vendingmachine.VendingMachineFactory;

import static org.junit.Assert.*;


public class VendingMachineTest {

    private static VendingMachine vm;

    @BeforeClass
    public static void setUp() {
        vm = VendingMachineFactory.createVendingMachine();
    }

    @AfterClass
    public static void tearDown() {
        vm = null;
    }
    
    @Test(expected = NotSufficientChangeException.class)
    public void testBuyItemWithMorePrice() {
        long price = vm.selectItemAndGetPrice(Item.COLDDRINK);
        assertEquals(Item.COLDDRINK.getPrice(), price);
        vm.insertCoin(Coin.QUARTER);
        vm.insertCoin(Coin.QUARTER);
        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();
        assertEquals(Item.COLDDRINK, item);
        assertTrue(!change.isEmpty());
        assertEquals(50 - Item.COLDDRINK.getPrice(), getTotal(change));
    }
    
    @Test
    public void testRefund() {
        long price = vm.selectItemAndGetPrice(Item.CANDY);
        assertEquals(Item.CANDY.getPrice(), price);
        vm.insertCoin(Coin.DIME);
        vm.insertCoin(Coin.NICKLE);
        vm.insertCoin(Coin.PENNY);
        vm.insertCoin(Coin.QUARTER);
        assertEquals(41, getTotal(vm.refund()));
    }

    @Test(expected = SoldOutException.class)
    public void testSoldOut() {
        for (int i = 0; i < 5; i++) {
            vm.selectItemAndGetPrice(Item.CHOCOLATES);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
        }
    }

    @Test(expected = NotSufficientChangeException.class)
    public void testNotSufficientChangeException() {
        for (int i = 0; i < 5; i++) {
            vm.selectItemAndGetPrice(Item.COLDDRINK);
            vm.insertCoin(Coin.QUARTER);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
            vm.selectItemAndGetPrice(Item.CANDY);
            vm.insertCoin(Coin.QUARTER);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
        }
    }

    @Test(expected = SoldOutException.class)
    public void testReset() {
        VendingMachine vmachine = VendingMachineFactory.createVendingMachine();
        vmachine.reset();
        vmachine.selectItemAndGetPrice(Item.CHOCOLATES);
    }
    
    @Test
    public void testBuyItemWithExactPrice() {
        long price = vm.selectItemAndGetPrice(Item.CHOCOLATES);
        assertEquals(Item.CHOCOLATES.getPrice(), price);
        vm.insertCoin(Coin.QUARTER);
        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();
        change.clear();
        assertEquals(Item.CHOCOLATES, item);
        assertTrue(change.isEmpty());
    }

    private long getTotal(List<Coin> change) {
        long total = 0;
        for (Coin c : change) {
            total = total + c.getDenomination();
        }
        return total;
    }
}


