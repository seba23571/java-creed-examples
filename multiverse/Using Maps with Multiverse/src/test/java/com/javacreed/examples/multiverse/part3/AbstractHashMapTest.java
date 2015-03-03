/*
 * #%L
 * Using Maps with Multiverse
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.multiverse.part3;

import org.multiverse.api.GlobalStmInstance;
import org.multiverse.api.Stm;
import org.multiverse.api.TxnThreadLocal;
import org.multiverse.api.collections.TxnMap;

public abstract class AbstractHashMapTest {

    protected <K, V> TxnMap<K, V> createMap() {
        final Stm stm = GlobalStmInstance.getGlobalStmInstance();
        TxnThreadLocal.clearThreadLocalTxn();

        return new BasicTxnHashMap<>(stm);
    }
}
