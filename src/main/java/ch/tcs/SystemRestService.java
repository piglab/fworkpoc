package ch.tcs;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/system")
public class SystemRestService
{
    @Autowired
    private UserDao userDao;

    @GET
    @Path("/")
    @Produces("application/json")
    public Response printMessage()
    {
        try
        {
            JSONObject json = new JSONObject();
            JSONArray array=new JSONArray();
            array.put("1");
            array.put("2");
            json.put("friends", array);
            User user = new User("toto", "tutu");
            userDao.save(user);
            String result = json.toString();
            return Response.status(200).entity(result).build();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}