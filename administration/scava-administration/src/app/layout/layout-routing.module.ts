import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';
import { RoleGuard } from '../shared/guard/role.guard';

const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: '', redirectTo: 'login' },
            { path: 'profile', loadChildren: './profile/profile.module#ProfileModule' },
            { path: 'password', loadChildren: './password/password.module#PasswordModule' },
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
