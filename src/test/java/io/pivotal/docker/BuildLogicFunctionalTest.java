package io.pivotal.docker;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class BuildLogicFunctionalTest {

  @Rule
  public final TemporaryFolder testProjectDir = new TemporaryFolder();

  @Test
  public void testHelloWorldTask() throws Exception {

    URL buildGradle = BuildLogicFunctionalTest.class.getResource("/build.gradle");
    assertThat(buildGradle).isNotNull();

    FileUtils.copyFileToDirectory(new File(buildGradle.getFile()), testProjectDir.getRoot());

    BuildResult result = GradleRunner.create()
        .withProjectDir(testProjectDir.getRoot())
        .withArguments("helloWorld")
        .withPluginClasspath()
        .build();

    assertThat(result.getOutput()).contains("Hello World!");
    assertThat(result.task(":helloWorld").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
  }

}
