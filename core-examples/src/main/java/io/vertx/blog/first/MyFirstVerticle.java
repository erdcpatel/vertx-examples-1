package io.vertx.blog.first;

import java.util.List;
import java.util.stream.Collectors;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.example.util.Runner;

public class MyFirstVerticle extends AbstractVerticle {
	
	EventBus eb;
	
	// Convenience method so you can run it in your IDE
    
    public static void main(String[] args) {
      Runner.runClusteredExample(MyFirstVerticle.class);
    }
    

  @Override
  public void start(Future<Void> fut) {
//    vertx
//        .createHttpServer()
//        .requestHandler(r -> {
//          r.response().end("<h1>Hello from my first " +
//              "Vert.x 3 application</h1>");
//        })
//        .listen(8080, result -> {
//          if (result.succeeded()) {
//            fut.complete();
//          } else {
//            fut.fail(result.cause());
//          }
//        });
	  
	  
	  eb = vertx.eventBus();

      Router router = Router.router(vertx);
      
      // Bind "/" to our hello message.
      router.route("/").handler(routingContext -> {
        HttpServerResponse response = routingContext.response();
        
        response
            .putHeader("content-type", "text/html")
            .end("<h1>Hello from my first Vert.x 3 application</h1>");
      });

      
      router.route("/ping").handler(this::ping);
      router.get("/api/getall").handler(this::getAll);


    vertx
        .createHttpServer()
        .requestHandler(router::accept)
        .listen(8080, result -> {
          if (result.succeeded()) {
            fut.complete();
          } else {
            fut.fail(result.cause());
          }
        });
	  
  }
  
  private void ping(RoutingContext routingContext) {
      String msg = routingContext.request().getParam("msg");
      eb.send("ping",msg,reply -> {
        if (reply.succeeded()) {
          String out = reply.result().body().toString();
          System.out.println("Received reply in server" + out);
          routingContext
          .response()
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(out);

        } else {
          System.out.println("No reply in server");

          routingContext
          .response()
          .putHeader("content-type", "application/json; charset=utf-8")
          .end("Failed");
        }

    });
   

  }
  
  private void getAll(RoutingContext routingContext) {
	  System.out.println("No reply in server");

      routingContext
      .response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end("get all");
	  }
  
}