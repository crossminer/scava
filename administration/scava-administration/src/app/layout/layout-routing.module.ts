import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: '', redirectTo: 'home' },
            { path: 'home', loadChildren: './home/home.module#HomeModule' },
            { path: 'profile', loadChildren: './profile/profile.module#ProfileModule' },
            { path: 'password', loadChildren: './password/password.module#PasswordModule' },
            { path: 'project', loadChildren: './project/project.module#ProjectModule' },
            { path: 'stacktraces', loadChildren: './stacktraces/stacktraces.module#StacktracesModule' },
            { path: 'properties', loadChildren: './properties/properties.module#PropertiesModule' },
            { path: 'worker', loadChildren: './worker/worker.module#WorkerModule' },
            { path: 'user-management', loadChildren: './user-management/user-management.module#UserManagementModule' },
            { path: 'metric-providers', loadChildren: './metric-providers/metric-providers.module#MetricProvidersModule' },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LayoutRoutingModule {}
