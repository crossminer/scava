import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: '', redirectTo: 'login' },
            { path: 'home', loadChildren: './home/home.module#HomeModule' },
            { path: 'project', loadChildren: './project/project.module#ProjectModule' },
            { path: 'user-management', loadChildren: './user-management/user-management.module#UserManagementModule' },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LayoutRoutingModule {}
