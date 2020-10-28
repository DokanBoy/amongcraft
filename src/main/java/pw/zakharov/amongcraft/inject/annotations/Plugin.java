package pw.zakharov.amongcraft.inject.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 28.10.2020 18:54
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

}
