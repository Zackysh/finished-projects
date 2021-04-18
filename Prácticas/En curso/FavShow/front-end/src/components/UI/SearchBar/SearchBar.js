import React from "react";
import "./style.css";

const SearchBar = ({ value, onChange, style }) => {
  return (
    <div className="form col-xs-12" style={style}>
      <input
        value={value}
        onChange={onChange}
        className="col-xs-9"
        id="searchBar"
        type="text"
        placeholder="search"
      />
    </div>
  );
};

export default SearchBar;
