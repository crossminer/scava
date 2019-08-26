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


my $project = shift;
if ( defined($project) ) {
    print "Retrievin tasks for project [$project]..\n";
} else {
    print "Retrieving tasks for all projects..\n";
}

my $url_auth = $base_url . ":8086/api/authentication";
my $url_tasks;
if ( defined($project) ) {
    $url_tasks = $base_url . ":8182/analysis/tasks/project/$project";
} else {
    $url_tasks = $base_url . ":8182/analysis/tasks";
}

# Prepare for http requests
my $ua  = Mojo::UserAgent->new;
my $auth;

sub _get_url() {
    my $url_in = shift;

    print "\n# Fetching ${url_in}.\n\n";
    my $res = $ua->get($url_in => {"Authorization" => $auth})->result;

    if (not $res->is_success) {
        print "Error: Could not get resource $url_in.\n";
        print Dumper($res->error) . "\n";
        exit 8;
    }

    # Decode JSON from server
    my $file_in = $url_in;
    $file_in =~ s!/!_!g;
    my $data = decode_json($res->body);

    return $data
}

# Authenticate
sub _get_auth() {
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
    print "  Authorisation OK.\n";

}


# Authenticate against API
&_get_auth();

# Send GET request
my $tasks = &_get_url($url_tasks); 

#print Dumper($tasks);
#exit;

print "# List of tasks:\n\n";

for my $task (@{$tasks}) {
    my $taskId = $task->{ 'analysisTaskId' } || "Unknown";
    my $startDate = $task->{ 'startDate' }{ '$date' } || "Unknown";
    my $endDate = $task->{ 'endDate' }{ '$date' } || "Unknown";
    my $status = $task->{ 'scheduling' }{ 'status' } || "Unknown";
    my $progress = $task->{ 'scheduling' }{ 'progress' } || "Unknown";
    my $measurement_vol = scalar( @{$task->{ 'metricExecutions' }} ) || "Unknown";
#     my $name = $project->{ 'name' } || "Unknown";
#     my $home_page = $project->{ 'homePage' } || "Unknown";
#     my $size = $project->{ 'size' } || 0;
#     my $its_url = $project->{ 'bugTrackingSystems' }[0]{ 'url' } || "Unknown";
#     my $scm_url = $project->{ 'vcsRepositories' }[0]{ 'url' } || "Unknown";
#     my $com_url = $project->{ 'communicationChannels' }[0]{ 'url' } || "Unknown";

    print "* $taskId\n";
    print "  - Start/End: $startDate - $endDate.\n";
    print "  - Status: $status\n";
    print "  - Progress: $progress\n";
    print "  - Measurements: $measurement_vol\n";
    print "\n";
}

