package io.vertx.blog.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;


/**
 * This is a verticle. A verticle is a _Vert.x component_. This verticle is implemented in Java, but you can
 * implement them in JavaScript, Groovy or even Ruby.
 */
public class Listener extends AbstractVerticle {

        EventBus eb;
     // Convenience method so you can run it in your IDE
        
        public static void main(String[] args) {
          Runner.runClusteredExample(Listener.class);
        }
        
  /**
   * This method is called when the verticle is deployed. It creates a HTTP server and registers a simple request
   * handler.
   * <p/>
   * Notice the `listen` method. It passes a lambda checking the port binding result. When the HTTP server has been
   * bound on the port, it call the `complete` method to inform that the starting has completed. Else it reports the
   * error.
   *
   * @param fut the future
   */
  @Override
  public void start(Future<Void> fut) {

      eb = vertx.eventBus();

	    eb.consumer("ping", message -> {

	      System.out.println("Received message In Listener: " + message.body());
	      // Now send back reply
	      message.reply("pong!" + message.body());
	    });

	    System.out.println("Listener started to lisen");
	}
}
