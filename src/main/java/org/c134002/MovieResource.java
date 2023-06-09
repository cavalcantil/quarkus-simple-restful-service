package org.c134002;

import com.oracle.svm.core.annotate.Delete;
import org.jboss.logging.annotations.Pos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Path("/movies")
public class MovieResource {
    public static List<Movie> movies = new ArrayList<>();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovie(){
        return Response.ok(movies).build();

    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countMovies(){
        return movies.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMovie(Movie newMovie) {
        movies.add(newMovie);
        return Response.ok(movies).build();
    }
    @PUT
    @Path("{id}/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(
            @PathParam("id")  Long id,
            @PathParam("title") String title){
//            @QueryParam("movie") String updateMovie) {
        movies = movies.stream().map(movie -> {
            if (movie.getId().equals(id)) {
                movie.setTitle(title);
            }
                return movie;

            }).collect(Collectors.toList());
        return  Response.ok(movies).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie (
            @PathParam("id") Long id){
       Optional <Movie> movieToDelete = movies.stream().filter(movies -> movies.getId().equals(id))
                .findFirst();
       boolean removed = false;
        if (movieToDelete.isPresent()) {
            removed = movies.remove(movieToDelete.get());
        }
        if (removed) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
