const fetch = require("node-fetch");
const { exec } = require("child_process");
const fs = require("fs").promises;

(async function () {
	console.log("Update PaperMC script offline.");
	console.log(
		"They've updated their site and rendered my scraper code obsolete."
	);

	const downloadLink = "Fetch from site...";

	// const { stdout, stderr } = await exec(`curl ${downloadLink} -o bukkit.jar`);

	// stdout.on("data", console.log);
	// stderr.on("data", console.log);
})();
