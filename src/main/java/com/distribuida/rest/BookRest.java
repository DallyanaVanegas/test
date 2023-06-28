package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.repo.BooksRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class BookRest {

    Logger logger = LoggerFactory.getLogger(BookRest.class);
    @Inject
    BooksRepository repo;
    @GET
    public List<Book> listarLibros() {
        return repo.listAll();
    }

    @GET
    @Path(value= "/{id}")
    public Book libroId(@PathParam("id") Long id) {
        logger.debug("Consultando libro con id={}",id);

        Book libro = Book.findById(id);
        return libro;
    }
    @POST
    public Response crearLibro(Book libro) {
        repo.persist(libro);

        return Response.status(Response.Status.CREATED)
                .entity(libro)
                .build();
    }

    @PUT
    @Path(value="/{id}")
    public Response actualizarLibro(@PathParam("id") Long id, Book updatedBook) {
        Book libro = repo.findById(id);

        libro.setIsbn(updatedBook.getIsbn());
        libro.setAutor(updatedBook.getAutor());
        libro.setAutor(updatedBook.getAutor());
        libro.setPrice(updatedBook.getPrice());

        repo.persistAndFlush(libro);
        return Response.ok().build();
    }

    @DELETE
    @Path(value="/{id}")
    public void borrarLibro(@PathParam("id") Long id) {
        repo.findById(id);
    }
}
