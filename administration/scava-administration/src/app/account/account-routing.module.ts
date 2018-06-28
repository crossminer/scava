import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    {
        children: [
            { path: '', redirectTo: 'login' },
            { path: 'activate', loadChildren: './activate/activate.module#ActivateModule' }
        ]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountRoutingModule { }
