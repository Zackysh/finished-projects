import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { Accordion, Button, Card } from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import FilledStar from "./UI/Icons/FilledStar";
import VoidStar from "./UI/Icons/VoidStar";
import "../styles/tables.css";

const Show = ({ show, existOnFavs, setFavs, favs, clickOnFav }) => {
  let buttonStyle = "btn btn-outline-success btn-floating";
  buttonStyle += existOnFavs(favs, show) ? " fav" : " noFav";

  return (
    <Accordion>
      <Card bg={"dark"} text={"white"} align={"center"} className="mb-2">
        <Card.Header>
          <Row>
            <Col xs={10}>
              <Accordion.Toggle
                as={Button}
                variant="link"
                eventKey="0"
                style={{ color: "white" }}
              >
                <h3 style={{ display: "block" }}>{show.title}</h3>
              </Accordion.Toggle>
            </Col>
            <Col>
              <button
                type="button"
                className={buttonStyle}
                data-mdb-ripple-color="dark"
                onClick={() => clickOnFav(setFavs, favs, show)}
              >
                {existOnFavs(favs, show) ? <FilledStar /> : <VoidStar />}
              </button>
            </Col>
          </Row>
        </Card.Header>
        <Accordion.Collapse eventKey="0">
          <Card.Body>
            <table className="table">
              <tbody>
                <tr>
                  <th>Title</th>
                  <td>{show.title}</td>
                </tr>
                <tr>
                  <th>Director</th>
                  <td>{show.director}</td>
                </tr>
                <tr>
                  <th>Cast</th>
                  <td>{show.cast}</td>
                </tr>
                <tr>
                  <th>Country</th>
                  <td>{show.country}</td>
                </tr>
                <tr>
                  <th>Date added</th>
                  <td>{show.date_added}</td>
                </tr>
                <tr>
                  <th style={{ width: "200px" }}>Release year</th>
                  <td>{show.release_year}</td>
                </tr>
                <tr>
                  <th>Rating</th>
                  <td>{show.rating}</td>
                </tr>
                <tr>
                  <th>Duration</th>
                  <td>{show.duration}</td>
                </tr>
                <tr>
                  <th>Listed in</th>
                  <td>{show.listed_in}</td>
                </tr>
                <tr>
                  <th>Description</th>
                  <td>{show.description}</td>
                </tr>
              </tbody>
            </table>
          </Card.Body>
        </Accordion.Collapse>
      </Card>
    </Accordion>
  );
};

export default Show;
