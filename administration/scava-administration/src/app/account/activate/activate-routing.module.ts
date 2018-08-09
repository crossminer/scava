import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ActivateComponent } from './activate.component';

const routes: Routes = [
    {
        path: '', component: ActivateComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ActivateRoutingModule { }
