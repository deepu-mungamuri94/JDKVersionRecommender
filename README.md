# JDKVersionRecommender

This project compares Host machines JDK version with JDK Versions available in [ZULU](https://app.swaggerhub.com/apis-docs/azul/zulu-download-community/1.0#/bundles/get_bundles_) and recommends latest JDK versions compatible for host machine.
<br/>
<br/>
<h2>Executable Instructions:</h2>
We can execute the app using following options:
<ul>
	<li>Jar Execution
		<ul>
			<li>Download the Jar available in the project: JavaUpdateRecommendator.jar</li>
			<li>Run jar using command: <b>java -jar JavaUpdateRecommendator.jar</b></li>
		</ul>
	</li>
	<li>Source Code Execution
		<ul>
			<li> Clone this project into your machine where we need to get the latest JDK Recommendations.</li>
			<li> Change your current working directory to: <b>src/com/java/recommender</b></li>
			<li> Execute java executable file(Main.java) here using following commands in sequence: 
				<ul>
					<li><b>javac Main.java</b></li>
					<li><b>java Main</b>.</li>
				</ul>
			</li>
		</ul>
	</li>
</ul>
<br/>
<h2>Output</h2>
<ul>
	<li>This will provide us the debug informations:
		<ul>
			<li>Installed JDK Version on host</li>
			<li>OS</li>
			<li>OS Architecture</li>
			<li>Table containing the updates avaiable: JDK-Version, Download URL</li>
		</ul>
	</li>
</ul>



