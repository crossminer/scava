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
use Text::CSV;

my $base_url;
if ( exists( $ENV{'SCAVA_HOST'} ) ) {
    $base_url = $ENV{'SCAVA_HOST'};
} else {
    $base_url = 'http://ci4.castalia.camp';
    #$base_url = 'http://scava-dev.ow2.org';
}

# Prepare for http requests
my $ua  = Mojo::UserAgent->new;

sub get_url() {
    my $url_in = shift;

    my $res = $ua->get($url_in)->result;

    if (not $res->is_success) {
        print "Error: Could not get resource $url_in.\n";
        print $res->message . "\n";
        exit;
    }

    # Decode JSON from server
    my $file_in = $url_in;
    $file_in =~ s!/!_!g;
#    &write_file($res->body, $file_in . '.json');
    my $data = decode_json($res->body);

    return $data
}


# Send GET request

my $m = &get_url($base_url . ":8182/metrics/");

my $csv = Text::CSV->new({binary => 1, eol => "\n"});
my @cols = ('id', 'name', 'description');
my $csv_out = join(',', @cols) . "\n";
for my $metric (@{$m}) {
    my @cs = (
	$metric->{'id'},
	$metric->{'name'},
	$metric->{'description'},
	);
    
    $csv->combine(@cs);
    $csv_out .= $csv->string();
}    

print $csv_out;



























