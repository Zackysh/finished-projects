import React, { useState, useEffect } from "react";
import axios from "axios";
// Model
import Shows from "./components/Shows";
// UI
import SearchTabs from "./components/UI/SearchTabs/SearchTabs";
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import "./app.css";
// Services
import favsService from "./services/favsService";
// Styles
import "./components/UI/SearchBar/style.css";

const App = () => {
  const [shows, setShows] = useState([]);
  const [filter, setFilter] = useState("");
  const [favs, setFavs] = useState([]);
  const [fav, setFav] = useState(false);

  useEffect(() => {
    axios
      .get("http://localhost:8081/api/shows")
      .then((response) => {
        const data = JSON.parse(response.data);
        if (data) setShows(data);
      })
      .catch((error) => {
        console.log(error);
        throw error;
      });
  }, []);

  useEffect(() => {
    favsService.downloadFavs(setFavs)
  }, []);

  return (
    <div className="application">
      <SearchTabs
        setFav={setFav}
        uploadFavs={favsService.uploadFavs}
        fav={fav}
        favs={favs}
        filter={filter}
        setFilter={setFilter}
      />
      <footer className="text-center">
        <Container>
          <Row>
            <Col>
              <Shows
                clickOnFav={favsService.clickOnFav}
                setFavs={setFavs}
                fav={fav}
                favs={favs}
                shows={
                  fav
                    ? favsService.showShows(favs, filter)
                    : favsService.showShows(shows, filter)
                }
                existOnFavs={favsService.existOnFavs}
              />
            </Col>
          </Row>
        </Container>
      </footer>
    </div>
  );
};

export default App;
