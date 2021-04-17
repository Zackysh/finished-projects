import React from 'react';

// UI Util
import PropTypes from 'prop-types';
import SwipeableViews from 'react-swipeable-views';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';

// UI
import SearchBar from './SearchBar'

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`full-width-tabpanel-${index}`}
      aria-labelledby={`full-width-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `full-width-tab-${index}`,
    'aria-controls': `full-width-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: '#39404A',
  },
}));

export default function FullWidthTabs({ filter, setFilter, setFav }) {
  // style ...
  const classes = useStyles();
  const theme = useTheme();
  // hooks :)
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => { // 0 -> shows 1 -> favs
    console.log('change', newValue);
    setFav(newValue === 1)
    setValue(newValue);
  };

  const handleChangeIndex = (index) => {
    setValue(index);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static" color="default">
        <button
          type="button"
          className="btn btn-outline-success btn-floating"
          data-mdb-ripple-color="dark"
        >Select favorites dump file</button>
        <Tabs
          value={value}
          onChange={handleChange}
          indicatorColor="primary"
          textColor="primary"
          variant="fullWidth"
          aria-label="full width tabs example"
        >
          <Tab label="Shows" {...a11yProps(0)} />
          <Tab label="Favorites" {...a11yProps(1)} />
        </Tabs>
      </AppBar>
      <SwipeableViews
        axis={theme.direction === 'rtl' ? 'x-reverse' : 'x'}
        index={value}
        onChangeIndex={handleChangeIndex}
      >
        {/* it throws warnings, but they are not relevant
            its not allowed to put */}
        <TabPanel value={value} index={0} dir={theme.direction}>
          <div className="container text-center" id="searchBox">
            <h1>NETFLIX Browser</h1>
            <SearchBar value={filter} onChange={event => setFilter(event.target.value)} />
            <br />
            <h2>Enter title, director, actor ...</h2>
          </div>
        </TabPanel>

        <TabPanel value={value} index={1} dir={theme.direction}>
          <div className="container text-center" id="searchBox">
            <h1 style={{ color: '#98FB98' }}>FAV Broser</h1>
            <div className="myform col-xs-12" style={{ border: '3px solid #98FB98' }}>
              <input value={filter} onChange={event => setFilter(event.target.value)} className="col-xs-9" id="searchBar" type="text" placeholder="search" />
            </div>
            <br />
            <h2 style={{ color: '#98FB98' }}>Enter title, director, actor ...</h2>
          </div>
        </TabPanel>

      </SwipeableViews>
    </div>
  );
}
