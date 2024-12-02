AtomicReference<Customer> updatedCustomer = null;
if (customer.getId() == null) { // crear
updatedCustomer.set(customerService.saveACustomer(customer));
} else { // editar
customerService.findACustomerById(customer.getId()).
ifPresentOrElse(optCustomer -> {
BeanUtils.copyProperties(customer, optCustomer);
updatedCustomer.set(customerService.saveACustomer(optCustomer));
}, () -> {
model.addAttribute("error", clientIdMsg);
updatedCustomer.set(null);
});
}


if (updatedCustomer.get() == null) {
if (!model.containsAttribute("error")) {
model.addAttribute("error", "Error al guardar.");
}
return "/error";
}
return "redirect:/customers";
}