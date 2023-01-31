package org.zbw.patchproducer.util;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.zbw.patchproducer.exception.PatchBuildRuntimeException;

import java.io.File;
import java.io.IOException;

public class JGitUtil {
    public static Repository openRepository(String gitDir) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.readEnvironment().setMustExist(true).setGitDir(new File(gitDir + "/.git")).build();
        if (repository.getObjectDatabase().exists()) {
            return repository;
        }
        throw new PatchBuildRuntimeException("未获取到仓库: " + gitDir);
    }
}
