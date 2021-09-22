import React from "react";
import "./style.css";

/* { value, onChange, style } */
export const FileInput = ({ buttonStyle, importFavs, favs, setFavs}) => {
  let fileInput = React.createRef();
  let fileReader;

  // Read file
  const handleSubmit = (event) => {
    event.preventDefault();
    if(fileInput.current.files[0]) {
      fileReader = new FileReader();
      fileReader.onloadend = handleFileRead;
      fileReader.readAsText(fileInput.current.files[0]);
    } else {
      window.alert("You haven't imported any files yet.")
    }
    
  };

  const handleFileRead = (e) => {
    try {
      const importedFavs = JSON.parse(fileReader.result);
      importFavs(importedFavs, favs, setFavs);
    } catch (error) {
      window.alert("Your favorites couldn't be imported! Try to import a well formed JSON.")
    }
  };

  return (
    <form onSubmit={handleSubmit} className="input-group input-group-prepend">
      <button className={buttonStyle} type="submit">
        Import favorites
      </button>

      <input className="input-group-text" type="file" ref={fileInput} />
    </form>
  );
};

export default FileInput;
