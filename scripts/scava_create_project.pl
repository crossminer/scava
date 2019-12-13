#! /usr/bin/env perl

######################################################################
# Copyright (c) 2017 Castalia Solutions
#
# This program and the accompanying materials are made
# available under the terms of the Eclipse Public License 2.0
# which is available at https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
######################################################################

use strict;
use warnings;

use Mojo::UserAgent;
use Mojo::JSON qw(decode_json encode_json);
use Data::Dumper;

my $base_url;
if ( exists( $ENV{'SCAVA_HOST'} ) ) {
    $base_url = $ENV{'SCAVA_HOST'};
} else {
    $base_url = 'http://ci4.castalia.camp';
    #$base_url = 'http://scava-dev.ow2.org';
}

if ( scalar(@ARGV) != 1 ) {
    print "Usage: $0 project\n";
    print "Example: \n";
    print "    $0 modeling.sirius \n";
    exit;
}

my $project = shift;

my %metric_providers = (
    "sentiment.SentimentHistoricMetricProvider",
    "severity.SeverityHistoricMetricProvider",
    );

my $url_auth = $base_url . ":8086/api/authentication";
my $url_create_project = $base_url . ":8086/administration/projects/import";
my $url_project = 'https://projects.eclipse.org/projects/' . $project;
    
# Prepare for http requests
my $ua  = Mojo::UserAgent->new;
my $auth;

# Authenticate against API
print "\n# Authenticating against ${base_url}.\n\n";
my $tx = $ua->post( 
    $url_auth => { 'Content-Type' => 'application/json'} => json => 
    {
	"username" => "admin",
	"password" => "admin",
    } 
    );

if (not $tx->result->is_success) {
    print "Error: Could not authenticate against API.\n";
    print Dumper($tx->result->error) . "\n";
    exit 8;
}

$auth = $tx->result->headers->authorization;
print "Authorisation is $auth.\n";


print "\n# Fetching ${url_create_project}.\n\n";
#my $res = $ua->get($url_in => {"Authorization" => $auth})->result;
$tx = $ua->post( $url_create_project => 
		    {"Authorization" => $auth} => 
		    json => { 'url' => $url_project } );

if (not $tx->result->is_success) {
    print "Error: Could not get resource $url_create_project.\n";
    print Dumper($tx->result->error) . "\n";
    exit 8;
}

# Decode JSON from server
my $data = decode_json($tx->result->body);

print "Project Created..\n\n";
#Dumper($data);

