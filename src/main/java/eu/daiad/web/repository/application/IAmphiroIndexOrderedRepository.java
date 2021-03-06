package eu.daiad.web.repository.application;

import org.joda.time.DateTimeZone;

import eu.daiad.web.model.amphiro.AmphiroMeasurementCollection;
import eu.daiad.web.model.amphiro.AmphiroMeasurementIndexIntervalQuery;
import eu.daiad.web.model.amphiro.AmphiroMeasurementIndexIntervalQueryResult;
import eu.daiad.web.model.amphiro.AmphiroSessionCollectionIndexIntervalQuery;
import eu.daiad.web.model.amphiro.AmphiroSessionCollectionIndexIntervalQueryResult;
import eu.daiad.web.model.amphiro.AmphiroSessionIndexIntervalQuery;
import eu.daiad.web.model.amphiro.AmphiroSessionIndexIntervalQueryResult;
import eu.daiad.web.model.amphiro.AmphiroSessionUpdateCollection;
import eu.daiad.web.model.device.AmphiroDevice;
import eu.daiad.web.model.error.ApplicationException;
import eu.daiad.web.model.security.AuthenticatedUser;

public interface IAmphiroIndexOrderedRepository {

    public AmphiroSessionUpdateCollection storeData(AuthenticatedUser user, AmphiroDevice device,
                    AmphiroMeasurementCollection data) throws ApplicationException;

    public abstract AmphiroMeasurementIndexIntervalQueryResult searchMeasurements(DateTimeZone timezone,
                    AmphiroMeasurementIndexIntervalQuery query);

    public abstract AmphiroSessionCollectionIndexIntervalQueryResult searchSessions(String[] name,
                    DateTimeZone timezone, AmphiroSessionCollectionIndexIntervalQuery query);

    public abstract AmphiroSessionIndexIntervalQueryResult getSession(AmphiroSessionIndexIntervalQuery query);

}