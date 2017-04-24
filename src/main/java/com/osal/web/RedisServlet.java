package com.osal.web;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RedisServlet extends HttpServlet {

  /* TODO
   * 1. make a constructor where you instantiate Jedis
   * 2. configure how the Jedis data structure works
   * 3. incorporate reading into doGet() and writing into doPost() 
   * */

  int counter = 0;
  private static JedisPool pool = null;

  @Override
  public void init() {
      pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);

  }

  @Override
  public void destroy() {
      pool.destroy();
  }

  /*public RedisServlet() {
      // counter = 10;
      // pool = new JedisPool(new JedisPoolConfig(), "localhost");
      jedis = pool.getResource();
      jedis.set("counter", "0");
      jedis.lpush("list", "counter");
      jedis.incr("counter");
      // counter += 1;
  }*/

  @Override
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    
    PrintWriter out = response.getWriter();
    Jedis jedis = null;

    try {
      jedis = pool.getResource();
      // jedis.set("counter", "0");
      jedis.lpush("list", Integer.toString(counter));
      counter += 10000;
      // jedis.incr("counter");
    } finally {
      pool.returnResource(jedis); 
    }

    try {
      // out.println("done successfully");
      String result = jedis.lpop("list");
      out.println(result);
    } finally {
      out.close();
    }


    //doPost(request, response);
    // jedis.lpush("list", "counter");
    // jedis.incr("counter");
    // counter += 1;
  }
/*
  @Override
  public void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    out.println("SimpleServlet Executed");
    String result = jedis.lpop("list");
    out.println(result);
    out.flush();
    out.close();
  }*/
}
