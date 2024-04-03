package me.dio.credit.application.system.repository

import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var testEntityManager: TestEntityManager

    @Test
    fun `should find customer by id`() {
        //given
        val customer: Customer = testEntityManager.persist(buildCustomer())
        //when
        val fakeCustomer: Optional<Customer> = customerRepository.findById(customer.id!!)
        //then
        Assertions.assertThat(fakeCustomer).isNotEmpty
        Assertions.assertThat(fakeCustomer.get()).isNotNull
        Assertions.assertThat(fakeCustomer.get().firstName).isEqualTo(customer.firstName)
        Assertions.assertThat(fakeCustomer.get().lastName).isEqualTo(customer.lastName)
        Assertions.assertThat(fakeCustomer.get().cpf).isEqualTo(customer.cpf)
        Assertions.assertThat(fakeCustomer.get().email).isEqualTo(customer.email)
        Assertions.assertThat(fakeCustomer.get().income).isEqualTo(customer.income)
        Assertions.assertThat(fakeCustomer.get().address).isNotNull
        Assertions.assertThat(fakeCustomer.get().address?.zipCode).isEqualTo(customer.address.zipCode)
        Assertions.assertThat(fakeCustomer.get().address?.street).isEqualTo(customer.address.street)
    }

    private fun buildCustomer(
        firstName: String = "Cami",
        lastName: String = "Cavalcante",
        cpf: String = "28475934625",
        email: String = "camila@gmail.com",
        password: String = "12345",
        zipCode: String = "12345",
        street: String = "Rua da Cami",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
    )
}