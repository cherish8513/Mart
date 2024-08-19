import org.assertj.core.api.Assertions

infix fun <A,B> A.shouldBe(that: B) = Assertions.assertThat(this).isEqualTo(that)