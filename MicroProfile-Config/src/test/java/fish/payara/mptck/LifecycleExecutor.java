package fish.payara.mptck;

import org.jboss.arquillian.container.spi.event.container.BeforeDeploy;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.util.logging.Logger;

public class LifecycleExecutor {

    private static final Logger LOG = Logger.getLogger(LifecycleExecutor.class.getName());

    public void executeBeforeDeploy(@Observes BeforeDeploy event, TestClass testClass) {
       LOG.info("before deploy event: " + event.getDeployment().getArchive().getName());
        Archive archive = event.getDeployment().getArchive();
        if (archive instanceof WebArchive) {
            WebArchive webArchive = WebArchive.class.cast(archive);
            if (webArchive.contains("WEB-INF/beans.xml")) {
                LOG.info("modify beans");
                webArchive.addAsWebInfResource("beans.xml", "beans.xml");
            }
        }
    }
}
