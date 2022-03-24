package neowise.user.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import neowise.user.service.constants.EnvType;

public class EnvHelper {

    private static Logger logger = LoggerFactory.getLogger(Env.class);
    private String SERVICE_ENV = System.getenv("SERVICE_ENV");
    private final EnvType DEFAULT_ENV = EnvType.DEV;
    private Dotenv dotenv;
    private String curEnv;

    private interface VarGetter {
        public String get(String key);
    }

    EnvHelper() {
        loadCurEnv();
        tryDotenvLoad();
    }

    private void loadCurEnv() {
        if (SERVICE_ENV == null) {
            curEnv = DEFAULT_ENV.toString();
        } else {
            this.curEnv = SERVICE_ENV;
        }
    }

    private String getCurEnv() {
        if (this.curEnv == null) {
            loadCurEnv();
        }

        return this.curEnv;
    }

    private void tryDotenvLoad() {
        try {
            String curEnv = getCurEnv();
            String envFile = String.format(".env." + curEnv);
            this.dotenv = Dotenv.configure().filename(envFile).load();
        } catch (DotenvException e) {
            logger.error("cannot load file: {}", ".env." + this.curEnv);
        }
    }

    private String getter(VarGetter getter, String key) {
        try {
            return getter.get(key);
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    public String get(String key) {
        String v1 = this.getter((k) -> System.getenv(k), key);
        String v2 = this.getter((k) -> this.dotenv.get(k), key);
        return v1 != null ? v1 : v2;
    }
}
