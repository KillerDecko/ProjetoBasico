package br.ce.wcaquino.core;

import static br.ce.wcaquino.core.DriverFactory.getDriver;
import static br.ce.wcaquino.core.DriverFactory.killDriver;

import java.io.File;
import java.io.IOException;

import br.ce.wcaquino.pages.LoginPage;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BaseTest {
	@Rule
	public TestName testName = new TestName();

	private LoginPage page = new LoginPage();

	@Before
	public void inicializa(){
		page.acessarTelaInicial();

		page.setEmail("andre.mendonca88@gmail.com");
		page.setSenha("123456");
		page.entrar();
	}

	@After
	public void finaliza() throws IOException{
		TakesScreenshot ss = (TakesScreenshot) getDriver();
		File arquivo = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + "screenshot" +
				File.separator + testName.getMethodName() + ".jpg"));

		if(Propriedades.FECHAR_BROWSER) {
			killDriver();
		}
	}

}
