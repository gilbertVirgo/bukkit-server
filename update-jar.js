const fetch = require("node-fetch");
const {exec} = require("child_process");

(async function() {
	const res = await fetch("https://papermc.io/api/v1/paper");
	const {versions} = await res.json();

	const download = `https://papermc.io/api/v1/paper/${versions[0]}/latest/download`;

	const { stdout, stderr } = await exec(`curl ${download} -o bukkit.jar`);

	stdout.on("data", console.log);
	stderr.on("data", console.log);
})();
