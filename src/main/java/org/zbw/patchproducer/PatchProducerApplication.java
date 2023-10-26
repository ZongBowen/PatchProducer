package org.zbw.patchproducer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zbw.patchproducer.exception.PatchBuildRuntimeException;
import org.zbw.patchproducer.util.LogUtil;
import org.zbw.patchproducer.util.PatchBuildUtil;

@SpringBootApplication
public class PatchProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PatchProducerApplication.class);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        try {
            PatchBuildUtil.buildPatch();
        } catch (PatchBuildRuntimeException e) {
            LogUtil.log(e.getMessage());
        }
    }
}
