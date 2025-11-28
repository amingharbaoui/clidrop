Dropzone.options.myAwesomeDropzone = {
    clickable: false,
    maxFiles: 4,
    init: function () {
        this.on("addedfile", function (file) {
            if (this.files.length > this.options.maxFiles) {
                this.removeFile(file);
            }

            for (let i = 0; i < this.files.length - 1; i++) {
                const f = this.files[i];

                if (f.name === file.name && f.size === file.size && f.lastModified === file.lastModified) {
                    this.removeFile(file);
                    break;
                }
            }
        });
    }
};