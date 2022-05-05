-- phpMyAdmin SQL Dump
-- version 3.5.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 22, 2015 at 02:40 PM
-- Server version: 5.5.29
-- PHP Version: 5.4.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `db_eventfinder`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_eventfinder_attendees`
--

CREATE TABLE `tbl_eventfinder_attendees` (
  `attendee_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `is_going` int(11) NOT NULL DEFAULT '-1',
  `is_interested` int(11) NOT NULL DEFAULT '-1',
  `is_invited` int(11) NOT NULL DEFAULT '-1',
  `user_id` int(11) NOT NULL DEFAULT '-1',
  `updated_at` int(11) NOT NULL DEFAULT '0',
  `created_at` int(11) NOT NULL DEFAULT '0',
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`attendee_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tbl_eventfinder_attendees`
--

INSERT INTO `tbl_eventfinder_attendees` (`attendee_id`, `event_id`, `is_going`, `is_interested`, `is_invited`, `user_id`, `updated_at`, `created_at`, `is_deleted`) VALUES
(1, 1, 1, -1, -1, 1, 1446963409, 1446963409, 0),
(2, 1, 1, -1, -1, 2, 1446963409, 1446963409, 0),
(3, 1, 1, -1, -1, 3, 1447584285, 1447584285, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_eventfinder_authentication`
--

CREATE TABLE `tbl_eventfinder_authentication` (
  `authentication_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `name` text NOT NULL,
  `role_id` int(11) NOT NULL,
  `is_deleted` int(11) NOT NULL,
  `deny_access` int(11) NOT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tbl_eventfinder_authentication`
--

INSERT INTO `tbl_eventfinder_authentication` (`authentication_id`, `username`, `password`, `name`, `role_id`, `is_deleted`, `deny_access`) VALUES
(1, 'admin', '3dba44de6dbf6ad13444799ed798f5b8', 'Admin', 1, 0, 0),
(2, 'guest', '25d55ad283aa400af464c76d713c07ad', 'Guest', 2, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_eventfinder_categories`
--

CREATE TABLE `tbl_eventfinder_categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category` text NOT NULL,
  `category_icon` text NOT NULL,
  `created_at` int(11) NOT NULL DEFAULT '0',
  `updated_at` int(11) NOT NULL DEFAULT '0',
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tbl_eventfinder_categories`
--

INSERT INTO `tbl_eventfinder_categories` (`category_id`, `category`, `category_icon`, `created_at`, `updated_at`, `is_deleted`) VALUES
(1, 'Conference', '', 1446963409, 1446963409, 0),
(2, 'Music Festival', '', 1446963409, 1446963409, 0),
(3, 'Concert', '', 1448197622, 1448197639, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_eventfinder_events`
--

CREATE TABLE `tbl_eventfinder_events` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `address` text NOT NULL,
  `event_desc` text NOT NULL,
  `gmt_date_set` datetime NOT NULL,
  `is_ticket_available` int(11) NOT NULL DEFAULT '-1',
  `lat` text NOT NULL,
  `lon` text NOT NULL,
  `ticket_url` text NOT NULL,
  `email_address` text NOT NULL,
  `contact_no` text NOT NULL,
  `title` text NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '-1',
  `is_featured` int(11) NOT NULL DEFAULT '0',
  `photo_url` text NOT NULL,
  `created_at` int(11) NOT NULL DEFAULT '0',
  `updated_at` int(11) NOT NULL DEFAULT '0',
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tbl_eventfinder_events`
--

INSERT INTO `tbl_eventfinder_events` (`event_id`, `address`, `event_desc`, `gmt_date_set`, `is_ticket_available`, `lat`, `lon`, `ticket_url`, `email_address`, `contact_no`, `title`, `user_id`, `is_featured`, `photo_url`, `created_at`, `updated_at`, `is_deleted`) VALUES
(1, 'Ridge Trail, South San Francisco, CA 94080, USA', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\\n\\nSed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\\n\\nBut I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?', '2017-12-16 08:30:00', 1, '37.68099454131698', '-122.41561878472567', 'www.google.com', 'developer@conference.com', '+1234567890', 'iOS Developer Conference', 1, 1, 'http://asianjournal.com/news/files/2013/06/apple.jpg', 1446963409, 1446963409, 0),
(2, '81-800 Avenue 51, Indio, CA 92201, United States', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', '2017-12-31 16:00:00', 1, '37.332333', '-122.031219', 'www.coachella.com', 'coachella@gmail.com', '+1234567890', 'Coachella 2017', 1, 1, 'http://media.nbcsandiego.com/images/1200*809/Coachella+Art+2015+%287%29.jpg', 1448122385, 1448172046, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_eventfinder_event_categories`
--

CREATE TABLE `tbl_eventfinder_event_categories` (
  `event_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL DEFAULT '0',
  `category_id` int(11) NOT NULL DEFAULT '0',
  `created_at` int(11) NOT NULL DEFAULT '0',
  `updated_at` int(11) NOT NULL DEFAULT '0',
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`event_category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tbl_eventfinder_event_categories`
--

INSERT INTO `tbl_eventfinder_event_categories` (`event_category_id`, `event_id`, `category_id`, `created_at`, `updated_at`, `is_deleted`) VALUES
(1, 1, 1, 1446963409, 1446963409, 0),
(2, 1, 2, 1446963409, 1446963409, 0),
(3, 2, 2, 1447668531, 1447668531, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_eventfinder_news`
--

CREATE TABLE `tbl_eventfinder_news` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT,
  `news_content` text NOT NULL,
  `news_title` text NOT NULL,
  `news_url` text NOT NULL,
  `photo_url` text NOT NULL,
  `created_at` int(11) NOT NULL,
  `updated_at` int(11) NOT NULL,
  `is_deleted` int(11) NOT NULL,
  PRIMARY KEY (`news_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tbl_eventfinder_news`
--

INSERT INTO `tbl_eventfinder_news` (`news_id`, `news_content`, `news_title`, `news_url`, `photo_url`, `created_at`, `updated_at`, `is_deleted`) VALUES
(1, 'Apparently, there’s still life in the old social video dog yet. FightMe, an iOS app that lets you challenge others by recording and sharing 30 second-long videos, has raised a further, modest seed round of funding.\\nNew backing comes from VC firm HTG Ventures, Daniel and Raphael Khalili (who also invested in Yahoo-acquired Summly), and David Reuben Junior of Reuben Brothers, who, together, have put $1.35 million into the London-based started. This adds to the $500,000 FightMe raised back in October 2013.\\nDescribed as a “social video network” designed to showcase any talent or opinion, the iOS app (with Android pegged to follow) lets you post your own 30-second, unedited videos, and add appropriate hashtags so that others can browse and discover your videos. They can then choose to respond with a similar themed video, as well as follow you, or share your video. &quot;asdasdasd&quot;', 'FightMe, An App That Lets You Challenge Others Through Video, Picks Up $1.35M In New Backing', 'http://techcrunch.com/2014/06/12/fightme-again/', 'http://tctechcrunch2011.files.wordpress.com/2014/06/screen-shot-fightme-singing.png?w=738', 1418883119, 1419349925, 0),
(2, 'There have been a lot of murmurs that Amazon would turn on a new music streaming service this week, and it looks like that’s just what it quietly did a little while ago. A link to Amazon Prime Music, if you are an Amazon Prime subscriber that is, now takes you to a page heavy on playlists, promising over 1 million songs ready for your streaming pleasure, on top of the downloading service that Amazon already offered. And Amazon has also relaunched its mobile app on iOS with a new name: it’s now called Amazon Music instead of Amazon Cloud Player, which now also features access to Amazon Prime Music.\\nBut, unless Amazon is going to add more features before an official unveiling, I’m not sure what we’re seeing right now is quite a Spotify killer. The service as it is right now appears to be missing the most current releases; and you have to go through several steps, including adding music to your library, before you can actually stream tracks. On a search results page, what you still get is the option to listen to an excerpt of a track.', 'Amazon Turns On Prime Music Streaming, Sans Current Hits', 'http://techcrunch.com/2014/06/11/amazon-turns-on-prime-music-streaming-sans-current-hits/', 'http://localhost/personal/storefinder/upload_pic/news_1404012277.png', 1404012277, 1404012277, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_eventfinder_posts`
--

CREATE TABLE `tbl_eventfinder_posts` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL DEFAULT '-1',
  `post` text NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '-1',
  `created_at` int(11) NOT NULL DEFAULT '0',
  `updated_at` int(11) NOT NULL DEFAULT '0',
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  `gmt_date_added` datetime NOT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `tbl_eventfinder_posts`
--

INSERT INTO `tbl_eventfinder_posts` (`post_id`, `event_id`, `post`, `user_id`, `created_at`, `updated_at`, `is_deleted`, `gmt_date_added`) VALUES
(1, 1, 'How to pay for the tickets?', 1, 1446963409, 1446963409, 0, '2015-11-17 08:14:00'),
(2, 1, 'Im going!', 2, 1446963409, 1446963409, 0, '2015-11-15 04:16:00'),
(3, 2, 'Can anyone confirm where I can buy the tickets?', 3, 1443980668, 1443980668, 0, '2015-11-19 09:38:00'),
(4, 1, 'Definitely going to this event.', 2, 1447920791, 1447920791, 0, '2015-11-19 08:13:11');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_eventfinder_users`
--

CREATE TABLE `tbl_eventfinder_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` text NOT NULL,
  `login_hash` text NOT NULL,
  `facebook_id` text NOT NULL,
  `twitter_id` text NOT NULL,
  `google_id` text NOT NULL,
  `email` text NOT NULL,
  `deny_access` int(11) NOT NULL,
  `thumb_url` text NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tbl_eventfinder_users`
--

INSERT INTO `tbl_eventfinder_users` (`user_id`, `full_name`, `login_hash`, `facebook_id`, `twitter_id`, `google_id`, `email`, `deny_access`, `thumb_url`) VALUES
(1, 'Megan Dee', 'jxEEcSkt2iWEJGOCZ/ShSxuo+rM3ODZkMjA5ZDc1', '261528977363938', '', '', 'mg.user001@gmail.com', 0, 'https://graph.facebook.com/261528977363938/picture?type=large'),
(2, 'Jonathan Moore', '8SX1w2qDgIA7pyshuLVT8/aDsjY4MzE4NWZmMDI2', '', '', '107545188502375828210', 'mg.user001@gmail.com', 0, 'http://img.faceyourmanga.com/mangatars/0/0/39/large_511.png'),
(3, 'John Doe', 'C/wZpX0U9rrQaCisLufUrsj79RpmMzEwMzhiYTg2', '', '2578207831', '', '', 0, 'https://trysomethingnewpimacounty.files.wordpress.com/2011/03/avatar-mike.jpg');