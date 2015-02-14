package com.javacreed.examples.concurrency.part5;

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnDoubleCallable;
import org.multiverse.api.collections.TxnList;

import java.util.Iterator;

public class Accounts {

    private final TxnList<Account> list;

    public Accounts(){
        list = StmUtils.newTxnLinkedList();
    }

    public void addAccount(final Account account) {
        StmUtils.atomic(new Runnable() {
            @Override
            public void run() {
                list.add(account);
            }
        });
    }

    public double calculateAverageBalance(){
        return StmUtils.atomic(new TxnDoubleCallable() {
            @Override
            public double call(Txn txn) throws Exception {
                if(list.isEmpty(txn)){
                    return 0;
                }

                int size = list.size(txn);
                double total = 0;
                // Iteration is not supported by Multiverse at this stage
                for(int i=0; i<size; i++){
                    Account account = list.get(txn, i);
                    total += account.getBalance(txn);
                }
                return total/size;
            }
        });
    }
}
