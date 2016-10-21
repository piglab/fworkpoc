package ch.tcs;

import org.json.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/system")
public class SystemRestService
{
    @GET
    @Path("/")
    @Produces("application/json")
    public Response printMessage()
    {
        JSONObject json = new JSONObject();
        JSONArray array=new JSONArray();
        array.put("1");
        array.put("2");
        try {
            json.put("friends", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String result = json.toString();

        return Response.status(200).entity(result).build();

    }
}