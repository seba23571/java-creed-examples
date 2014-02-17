/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.slf4j.part2;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.Marker;

public class AsynchLogger implements Logger {

  private static final RejectedExecutionHandler DEFAULT_REJECTION_HANDLER = new RejectedExecutionHandler() {
    @Override
    public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
      // Ignore
    }
  };

  private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {

    private final AtomicLong threadNumber = new AtomicLong(1L);

    private final ThreadGroup group;

    {
      final SecurityManager securityManager = System.getSecurityManager();
      group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(final Runnable r) {
      final String threadName = String.format("ASYNCH-LOGGER-THREAD-%d", threadNumber.getAndIncrement());
      final Thread thread = new Thread(group, r, threadName, 0);
      thread.setDaemon(true);
      if (thread.getPriority() != Thread.NORM_PRIORITY) {
        thread.setPriority(Thread.NORM_PRIORITY);
      }
      return thread;
    }
  };

  private final Logger delegator;

  private volatile ThreadPoolExecutor executor;

  public AsynchLogger(final Logger delegator) {
    this.delegator = Objects.requireNonNull(delegator);
  }

  @Override
  public void debug(final Marker marker, final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(marker, msg);
      }
    });
  }

  @Override
  public void debug(final Marker marker, final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(marker, format, arg);
      }
    });
  }

  @Override
  public void debug(final Marker marker, final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(marker, format, arguments);
      }
    });
  }

  @Override
  public void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(marker, format, arg1, arg2);
      }
    });
  }

  @Override
  public void debug(final Marker marker, final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(marker, msg, t);
      }
    });
  }

  @Override
  public void debug(final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(msg);
      }
    });
  }

  @Override
  public void debug(final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(format, arg);
      }
    });
  }

  @Override
  public void debug(final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(format, arguments);
      }
    });
  }

  @Override
  public void debug(final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(format, arg1, arg2);
      }
    });
  }

  @Override
  public void debug(final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.debug(msg, t);
      }
    });
  }

  @Override
  public void error(final Marker marker, final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(marker, msg);
      }
    });
  }

  @Override
  public void error(final Marker marker, final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(marker, format, arg);
      }
    });
  }

  @Override
  public void error(final Marker marker, final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(marker, format, arguments);
      }
    });
  }

  @Override
  public void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(marker, format, arg1, arg2);
      }
    });
  }

  @Override
  public void error(final Marker marker, final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(marker, msg, t);
      }
    });
  }

  @Override
  public void error(final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(msg);
      }
    });
  }

  @Override
  public void error(final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(format, arg);
      }
    });
  }

  @Override
  public void error(final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(format, arguments);
      }
    });
  }

  @Override
  public void error(final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(format, arg1, arg2);
      }
    });
  }

  @Override
  public void error(final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.error(msg, t);
      }
    });
  }

  @Override
  public String getName() {
    return delegator.getName();
  }

  @Override
  public void info(final Marker marker, final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(marker, msg);
      }
    });
  }

  @Override
  public void info(final Marker marker, final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(marker, format, arg);
      }
    });
  }

  @Override
  public void info(final Marker marker, final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(marker, format, arguments);
      }
    });
  }

  @Override
  public void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(marker, format, arg1, arg2);
      }
    });
  }

  @Override
  public void info(final Marker marker, final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(marker, msg, t);
      }
    });
  }

  @Override
  public void info(final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(msg);
      }
    });
  }

  @Override
  public void info(final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(format, arg);
      }
    });
  }

  @Override
  public void info(final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(format, arguments);
      }
    });
  }

  @Override
  public void info(final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(format, arg1, arg2);
      }
    });
  }

  @Override
  public void info(final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.info(msg, t);
      }
    });
  }

  public void init() {
    executor = new ThreadPoolExecutor(1, 4, // Minimum and maximum number of threads
        0L, TimeUnit.MILLISECONDS, // The maximum idle time
        new LinkedBlockingQueue<Runnable>(16), // the queue
        AsynchLogger.THREAD_FACTORY, AsynchLogger.DEFAULT_REJECTION_HANDLER);// The rejection handler
  }

  @Override
  public boolean isDebugEnabled() {
    return delegator.isDebugEnabled();
  }

  @Override
  public boolean isDebugEnabled(final Marker marker) {
    return delegator.isDebugEnabled(marker);
  }

  @Override
  public boolean isErrorEnabled() {
    return delegator.isErrorEnabled();
  }

  @Override
  public boolean isErrorEnabled(final Marker marker) {
    return delegator.isErrorEnabled(marker);
  }

  @Override
  public boolean isInfoEnabled() {
    return delegator.isErrorEnabled();
  }

  @Override
  public boolean isInfoEnabled(final Marker marker) {
    return delegator.isInfoEnabled(marker);
  }

  @Override
  public boolean isTraceEnabled() {
    return delegator.isTraceEnabled();
  }

  @Override
  public boolean isTraceEnabled(final Marker marker) {
    return delegator.isTraceEnabled(marker);
  }

  @Override
  public boolean isWarnEnabled() {
    return delegator.isWarnEnabled();
  }

  @Override
  public boolean isWarnEnabled(final Marker marker) {
    return delegator.isWarnEnabled(marker);
  }

  public void registerShutdownHook() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        AsynchLogger.this.shutdown();
      }
    });
  }

  public void shutdown() {
    if (executor != null) {
      executor.shutdownNow();
    }
  }

  @Override
  public void trace(final Marker marker, final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(marker, msg);
      }
    });
  }

  @Override
  public void trace(final Marker marker, final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(marker, format, arg);
      }
    });
  }

  @Override
  public void trace(final Marker marker, final String format, final Object... argArray) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(marker, format, argArray);
      }
    });
  }

  @Override
  public void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(marker, format, arg1, arg2);
      }
    });
  }

  @Override
  public void trace(final Marker marker, final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(marker, msg, t);
      }
    });
  }

  @Override
  public void trace(final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(msg);
      }
    });
  }

  @Override
  public void trace(final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(format, arg);
      }
    });
  }

  @Override
  public void trace(final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(format, arguments);
      }
    });
  }

  @Override
  public void trace(final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(format, arg1, arg2);
      }
    });
  }

  @Override
  public void trace(final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.trace(msg, t);
      }
    });
  }

  @Override
  public void warn(final Marker marker, final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(marker, msg);
      }
    });
  }

  @Override
  public void warn(final Marker marker, final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(marker, format, arg);
      }
    });
  }

  @Override
  public void warn(final Marker marker, final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(marker, format, arguments);
      }
    });
  }

  @Override
  public void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(marker, format, arg1, arg2);
      }
    });
  }

  @Override
  public void warn(final Marker marker, final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(marker, msg, t);
      }
    });
  }

  @Override
  public void warn(final String msg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(msg);
      }
    });
  }

  @Override
  public void warn(final String format, final Object arg) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(format, arg);
      }
    });
  }

  @Override
  public void warn(final String format, final Object... arguments) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(format, arguments);
      }
    });
  }

  @Override
  public void warn(final String format, final Object arg1, final Object arg2) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(format, arg1, arg2);
      }
    });
  }

  @Override
  public void warn(final String msg, final Throwable t) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        delegator.warn(msg, t);
      }
    });
  }
}
