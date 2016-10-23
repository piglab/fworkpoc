package ch.tcs;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class MessageApplication extends Application //oth 2
{
    private Set<Object> singletons = new HashSet<Object>();

    public MessageApplication()

    {
        singletons.add(new MessageRestService());
        singletons.add(new SystemRestService());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}