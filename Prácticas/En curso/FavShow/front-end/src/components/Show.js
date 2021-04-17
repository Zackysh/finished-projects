import React from 'react'
import { Accordion, Card, Button } from 'react-bootstrap'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/tables.css'

const Show = ({ show, existOnFavs, clickOnFav }) => {
    console.log(typeof(existOnFavs));

    const voidStar =
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-star" viewBox="0 0 16 16">
            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z" />
        </svg>
    const filledStar =
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-star-fill" viewBox="0 0 16 16">
            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z" />
        </svg>

    return (
        <Accordion>
            <Card
                bg={'dark'}
                text={'white'}
                align={'center'}
                style={{ width: '40rem' }}
                className="mb-2"
            >
                <Card.Header>
                    <Row>
                        <Col xs={10}>
                            <Accordion.Toggle as={Button} variant='link' eventKey="0" style={{ color: 'white' }}>
                                <h3 style={{ display: 'block' }}>{show.title}</h3>
                            </Accordion.Toggle>
                        </Col>
                        <Col>
                            <button
                                type="button"
                                className="btn btn-outline-success btn-floating"
                                data-mdb-ripple-color="dark"
                                onClick={() => clickOnFav(show)}
                            >
                                {existOnFavs(show) ? filledStar : voidStar}
                            </button>
                        </Col>
                    </Row>
                </Card.Header>
                <Accordion.Collapse eventKey="0">
                    <Card.Body>
                        <table className="table">
                            <tbody>
                                <tr><th>Title</th><td>{show.title}</td></tr>
                                <tr><th>Director</th><td>{show.director}</td></tr>
                                <tr><th>Cast</th><td>{show.cast}</td></tr>
                                <tr><th>Country</th><td>{show.country}</td></tr>
                                <tr><th>Date added</th><td>{show.date_added}</td></tr>
                                <tr><th style={{ width: '200px' }}>Release year</th><td>{show.release_year}</td></tr>
                                <tr><th>Rating</th><td>{show.rating}</td></tr>
                                <tr><th>Duration</th><td>{show.duration}</td></tr>
                                <tr><th>Listed in</th><td>{show.listed_in}</td></tr>
                                <tr><th>Description</th><td>{show.description}</td></tr>
                            </tbody>
                        </table>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        </Accordion>
    )
}

export default Show