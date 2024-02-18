import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from './core/not-found/not-found.component';
import {LoginComponent} from "./account/login/login.component";

const routes: Routes = [
  {path:'', component: LoginComponent, data:{breadcrumb: 'Home'}},
  {path:'store', loadChildren:()=>import('./store/store.module').then(m=>m.StoreModule)},
  {path:'basket', loadChildren:()=>import('./basket/basket.module').then(m=>m.BasketModule)},
  {path:'account', loadChildren:()=>import('./account/account.module').then(m=>m.AccountModule)},
  {path:'checkout', loadChildren:()=>import('./checkout/checkout.module').then(m=>m.CheckoutModule)},
  {path:'not-found', component: NotFoundComponent},
  {path:'**', redirectTo: '', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
