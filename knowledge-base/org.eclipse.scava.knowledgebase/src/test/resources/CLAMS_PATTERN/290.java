{
    final VmInstance vm;
    for ( Address address : Addresses.getInstance().listValues() ) {
        if ( vm.getInstanceId().equals( address.getInstanceId() ) ) {
            if( address.getInstanceAddress() != null ) {
                SystemState.dispatch( vm.getPlacement(), new UnassignAddressCallback( address ), Admin.makeMsg( UnassignAddressType.class, address.getName(), address.getInstanceAddress() ) );
            }
            if( EucalyptusProperties.NAME.equals( address.getUserId() ) ) {
                try {
                    (new AddressManager()).ReleaseAddress( Admin.makeMsg( ReleaseAddressType.class, address.getName() ) );
                } catch ( EucalyptusCloudException e ) {
                    // Do something
                }
            }
        }
    }
    SystemState.dispatch( vm.getPlacement(), new TerminateCallback(), Admin.makeMsg( TerminateInstancesType.class, vm.getInstanceId() ) );
}
