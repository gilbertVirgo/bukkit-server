const fs = require("fs");
const path = require("path");

const logError = ({title = "There was an error...", err}) => {
	console.log("-----");
	console.log(title)
	console.log(err);
	console.log("-----");
}

const sortByDate = async ({parentDir, fileNames}) => {
	if(fileNames) {
		const fileData = fileNames.map(fileName => {
			const filePath = path.join(parentDir, fileName);
			const time = fs.statSync(filePath).mtime.getTime();

			return {fileName, time}
		});

		// Sort by latest first
		const sortedFileData = fileData.sort((a, b) => b.time - a.time );
		const sortedNames = sortedFileData.map(({fileName}) => fileName);

		return sortedNames;
	} else return [];
}

// Get jar files starting with "spigot-" in folder
const init = async () => {
	const parentDir = path.resolve(__dirname);
	const fileNames = await fs.promises.readdir(parentDir);

	try {
		const spigotFiles = await sortByDate({
			parentDir,
			fileNames: fileNames.filter(fileName => fileName.includes("spigot-") && fileName.includes(".jar"))
		});

		if(spigotFiles.length) {
			try {
				await fs.promises.unlink(path.resolve(__dirname, "bukkit.jar"));
				await fs.promises.rename(path.resolve(__dirname, spigotFiles[0]), "bukkit.jar");
			} catch(err) {
				logError({
					title: "There was an error replacing the old bukkit.jar file...",
					err
				});
			}
		}
	} catch(err) {
		logError({
			title: "There was an error sorting the spigot files by date...",
			err
		})
	}
}

init();